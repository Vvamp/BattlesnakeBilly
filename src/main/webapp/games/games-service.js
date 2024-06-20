export default class GamesService {
    async getGameIds() {
        return fetch("/api/games", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + window.sessionStorage.getItem("loginToken")
            }
        }).then(function(response){
            if(response.ok){
                return response.json();
            }
        }).then(function(gameIds) {
            return Promise.resolve(gameIds)
        });
    }

    async getReplay(gameId) {

        //zolang je maar laat zien dat je data kunt opslaan over meerdere zetten heen. Dus deze dummy-data is puur
        //ter illustratie.
        return fetch("/api/games/" + gameId, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + window.sessionStorage.getItem("loginToken")
            }
        }).then(function(response){
            if(response.ok){
                console.log("response ok replay");
                return response.json();
            }
            console.log("response NOT ok");
        }).then(function(game) {
            console.log("return replay: " + game);
            return Promise.resolve(game)
        });
    }

    async removeReplay(gameId) {
        return fetch("/api/games/" + gameId, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + window.sessionStorage.getItem("loginToken")
            }
        }).then(function(response){
            if(response.ok){
                return response.json();
            }
        }).then(function(game) {
            return Promise.resolve(game)
        });
    }
}
