export default class GamesService {
    async getGameIds() {
        return fetch("/api/games", {
            method: "GET"
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
            method: "GET"
        }).then(function(response){
            if(response.ok){
                return response.json();
            }
        }).then(function(game) {
            return Promise.resolve(game)
        });
    }

    async removeReplay(gameId) {
        return fetch("/api/games/" + gameId, {
            method: "DELETE"
        }).then(function(response){
            if(response.ok){
                return response.json();
            }
        }).then(function(game) {
            return Promise.resolve(game)
        });
    }
}
