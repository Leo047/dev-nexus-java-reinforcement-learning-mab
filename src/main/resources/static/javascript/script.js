const usernameElement = document.getElementById('username');
const adsContainer = document.getElementById('adsContainer');
const loginButton = document.getElementById('loginButton');
const logoutButton = document.getElementById('logoutButton');

let currentUser = 'guest';

async function loadAd() {
    adsContainer.innerHTML = '';
    const userAd = await fetchAdForUser(currentUser);
    const adElement = document.createElement('div');
    adElement.classList.add('ad');
    adElement.innerHTML = `
                   <h3>${userAd.adId}</h3>
                  <p>${userAd.adContent}</p>
                  <button onclick="reportAdClick('${userAd.adId}')">Click Ad</button>
            `;
    adsContainer.appendChild(adElement);

}

async function reportAdClick(adId) {
    try {
        const response = await fetch(`/api/v1/ads/reward?adId=${adId}&userId=${currentUser}`, {
            method: 'POST'
        });
        if (!response.ok) {
            throw new Error('Failed to report ad click');
        }
        console.log('Ad click reported successfully');
    } catch (error) {
        console.error('Error reporting ad click:', error);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const loginButton = document.getElementById('loginButton');
    if (loginButton) {
        loginButton.addEventListener('click', function () {
            currentUser = 'user1'; // Simulate login for user1
            usernameElement.textContent = currentUser;
            loginButton.style.display = 'none';
            logoutButton.style.display = 'block';
            loadAd().then(r => console.log('Ad loaded for user1'));
        });
    }
});

async function fetchAdForUser(user) {
    try {
        console.log('Fetching ad for user:', user);
        const response = await fetch(`/api/v1/ads/getAd?userId=${user}`);
        if (!response.ok) {
            console.error('Failed to fetch ads:', response.statusText);
            throw new Error('Failed to fetch ads');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching ads:', error);
        return [
            {title: "Default Ad", description: "Check out our latest deals!"}
        ];
    }
}

loadAd().then(r => console.log('Ad loaded for guest user'));


logoutButton.addEventListener('click', function () {
    usernameElement.textContent = 'guest';
    loginButton.style.display = 'inline';
    logoutButton.style.display = 'none';
});

