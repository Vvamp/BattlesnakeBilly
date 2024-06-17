export default class LoginService {
    isLoggedIn() {
        //TODO: hoe ga je bepalen of iemand ingelogd is (geweest)?
        return false;
    }

    login(user, password) {
        //TODO: inloggen met POST
        return Promise.resolve();
    }

    getUser() {
        //TODO: deze GET method test je token op server-side problemen. Je kunt client-side op zich wel 'ingelogd' zijn
        //maar het zou altijd zomaar kunnen dat je token verlopen is, of dat er server-side iets anders aan de hand is.
        //Dus het is handig om te checken met een -echte fetch- of je login-token wel echt bruikbaar is.
        return Promise.resolve(null);
    }

    logout() {
        //TODO: hoe ga je eigenlijk iemand 'uitloggen'?
        return Promise.resolve();
    }
}
