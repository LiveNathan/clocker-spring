// Import IDB script
importScripts('https://cdn.jsdelivr.net/npm/idb@8/build/umd.js');
console.log("IDB script imported");

// Create or open IndexedDB
const openDatabase = async () => {
    return idb.openDB('offline-requests', 1, {
        upgrade(db) {
            if (!db.objectStoreNames.contains('requests')) {
                db.createObjectStore('requests', { keyPath: 'id', autoIncrement: true });
            }
            console.log('Database upgraded');
        },
    });
};

// Stash request in IndexedDB
const stashRequest = async (request) => {
    try {
        console.log('Stashing request...');
        const db = await openDatabase();
        const tx = db.transaction('requests', 'readwrite');
        const store = tx.objectStore('requests');
        const clone = request.clone();
        let item = {};
        if (request.headers.get('Content-Type') && request.headers.get('Content-Type').includes('application/json')) {
            item = await clone.json().catch(err => {
                console.error('JSON parsing error:', err);
                return {};  // Default to empty object if parsing fails
            });
        }
        await store.put({
            method: request.method,
            url: request.url,
            body: item,
        });
        await tx.done;  // Ensure transaction is done
        return new Response(
            JSON.stringify({ message: 'Request stashed due to offline mode.' }),
            { status: 503, headers: { 'Content-Type': 'application/json' } }
        );
    } catch (err) {
        console.error('Error stashing request:', err);
        return new Response(
            JSON.stringify({ message: 'Failed to stash request.' }),
            { status: 500, headers: { 'Content-Type': 'application/json' } }
        );
    }
};

// Resend cached requests
// Resend cached requests
const resendRequests = async () => {
    console.log('Resending requests...');
    const db = await openDatabase();
    const tx = db.transaction('requests', 'readwrite');
    const store = tx.objectStore('requests');
    const allRequests = await store.getAll();
    await Promise.all(
        allRequests.map(async (req) => {
            const { method, url, body } = req;
            try {
                const response = await fetch(url, {
                    method,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(body),
                });
                if (response.ok) {
                    // Only delete the request if the fetch was successful
                    console.log(`Request to ${url} was successful. Deleting from IndexedDB.`);
                    const deleteTx = db.transaction('requests', 'readwrite');
                    const deleteStore = deleteTx.objectStore('requests');
                    await deleteStore.delete(req.id);
                    await deleteTx.done;  // Ensure delete transaction is done
                } else {
                    console.error(`Request to ${url} failed with status: ${response.status}`);
                }
            } catch (error) {
                console.error(`Failed to resend request to ${url}:`, error);
            }
        })
    );
    await tx.done;  // Ensure transaction is done

    // Notify the client
    self.clients.matchAll().then(clients => {
        clients.forEach(client => client.postMessage('update'));
    });
};
// Service worker events
self.addEventListener('install', (event) => {
    console.log('Service Worker installing.');
    self.skipWaiting();
});

self.addEventListener('activate', (event) => {
    console.log('Service Worker activating.');
    event.waitUntil(self.clients.claim());
});

self.addEventListener('fetch', (event) => {
    if (event.request.method === 'POST') {
        event.respondWith(
            (async () => {
                try {
                    const response = await fetch(event.request.clone());
                    return response;
                } catch (err) {
                    return stashRequest(event.request);
                }
            })()
        );
    }
});

self.addEventListener('sync', (event) => {
    if (event.tag === 'sync-requests') {
        event.waitUntil(resendRequests());
    }
});

self.addEventListener('online', (event) => {
    event.waitUntil(resendRequests());
});