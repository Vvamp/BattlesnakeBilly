export default class SnakeService {
    async getSnake() {

        let username = window.sessionStorage.getItem("user");

        return fetch("/api/snakes/" + username, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + window.sessionStorage.getItem("loginToken")
            }
        }).then(function(response){
            if(response.ok){
                return response.json();
            }
        }).then(function(snake){
            return Promise.resolve({
                apiversion: snake.apiversion,
                author: snake.author,
                color: snake.color,
                head: snake.head,
                tail: snake.tail,
                version: snake.version
            })
        });
    }

    async updateSnake(updatedSnake) {
        let fetchoptions = {
            method: "PUT",
            body: JSON.stringify(updatedSnake),
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + window.sessionStorage.getItem("loginToken")
            }
        };
        let username = window.sessionStorage.getItem("user");

        return fetch("/api/snakes/" + username, fetchoptions)
            .then(function(response){
               if(response.ok) {
                   return response.json();
               }
            }).then(function(result){
                return Promise.resolve(result);
        });
    }
}
