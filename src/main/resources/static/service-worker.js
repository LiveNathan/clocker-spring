self.addEventListener('install', event => {
    self.skipWaiting(); // Activate the service worker immediately
});

self.addEventListener('activate', event => {
    event.waitUntil(self.clients.claim()); // Take control of all open clients
});

self.addEventListener('fetch', event => {
    const requestUrl = new URL(event.request.url);

    // Bypass the service worker for Chrome extensions and POST requests to avoid issues
    if (requestUrl.protocol.startsWith('chrome-extension') || event.request.method === 'POST') {
        return;
    }

    event.respondWith(
        caches.match(event.request).then(cachedResponse => {
            return cachedResponse || fetch(event.request).then(networkResponse => {
                return caches.open('cache').then(cache => {
                    cache.put(event.request, networkResponse.clone());
                    return networkResponse;
                });
            });
        }).catch(() => {
            return new Response('', { status: 404 }); // Fallback to 404 if the fetch fails
        })
    );
});

self.addEventListener('sync', event => {
    if (event.tag === 'sync-clockOut-requests') {
        event.waitUntil(syncClockOutRequests());
    }
});

async function syncClockOutRequests() {
    const db = await openIndexedDB('ClockAppDB', 1);
    const transaction = db.transaction('offlineRequests', 'readonly');
    const store = transaction.objectStore('offlineRequests');
    const allRequests = await store.getAll();

    for (const requestData of allRequests) {
        try {
            await fetch(requestData.url, {
                method: requestData.method,
                headers: new Headers(requestData.headers),
                body: requestData.body
            });
            const deleteTransaction = db.transaction('offlineRequests', 'readwrite');
            deleteTransaction.objectStore('offlineRequests').delete(requestData.id);
        } catch (error) {
            console.error('Failed to sync request:', error);
        }
    }
}

async function openIndexedDB(name, version) {
    return new Promise((resolve, reject) => {
        const request = indexedDB.open(name, version);

        request.onupgradeneeded = function () {
            request.result.createObjectStore('offlineRequests', { autoIncrement: true });
        };

        request.onsuccess = function () {
            resolve(request.result);
        };

        request.onerror = function () {
            reject(request.error);
        };
    });
}
