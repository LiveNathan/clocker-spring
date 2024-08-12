// Check if idb is loaded
if (typeof idb !== 'undefined') {
    console.log('idb is accessible');
} else {
    console.log('idb is not accessible');
}
/*
// Create or open IndexedDB
const openDatabase = async () => {
    return idb.openDB('offline-requests', 1, {
        upgrade(db) {
            if (!db.objectStoreNames.contains('requests')) {
                db.createObjectStore('requests', { keyPath: 'id', autoIncrement: true });
            }
        },
    });
};

// Stash request in IndexedDB
const stashRequest = async (request) => {
    const db = await openDatabase();
    const tx = db.transaction('requests', 'readwrite');
    const store = tx.objectStore('requests');
    const item = await request.json();
    await store.put({
        method: request.method,
        url: request.url,
        body: item,
    });
    await tx.done;
    return new Response(JSON.stringify({ message: 'Request stashed due to offline mode.' }), { status: 503 });
};

// Resend cached requests
const resendRequests = async () => {
    const db = await openDatabase();
    const tx = db.transaction('requests', 'readwrite');
    const store = tx.objectStore('requests');
    const allRequests = await store.getAll();
    for (const req of allRequests) {
        const { method, url, body } = req;
        await fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body),
        });
        await store.delete(req.id);
    }
    await tx.done;
};

self.addEventListener('install', (event) => {
    console.log('Service Worker installing.');
    self.skipWaiting();
});

self.addEventListener('activate', (event) => {
    console.log('Service Worker activating.');
    const CACHE_NAME = 'v1';
    const cacheWhitelist = [CACHE_NAME];
    event.waitUntil(
        caches.keys().then(cacheNames => {
            return Promise.all(
                cacheNames.map(cacheName => {
                    if (!cacheWhitelist.includes(cacheName)) {
                        console.log('Deleting old cache:', cacheName);
                        return caches.delete(cacheName);
                    }
                })
            );
        }).then(() => {
            console.log('Cache cleared.');
            return self.clients.claim();
        })
    );
});

self.addEventListener('fetch', (event) => {
    if (event.request.method === 'POST') {
        event.respondWith(
            fetch(event.request).catch(() => stashRequest(event.request.clone()))
        );
    }
});

self.addEventListener('sync', (event) => {
    if (event.tag === 'sync-requests') {
        event.waitUntil(resendRequests());
    }
});

// Listen for the online event
self.addEventListener('online', (event) => {
    event.waitUntil(resendRequests());
});

 */