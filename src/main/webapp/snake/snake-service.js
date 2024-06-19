export default class SnakeService {
    async getSnake() {


        return fetch("/api/snake", {
            method: "GET"
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
            headers: {"Content-Type": "application/json"}
        };

        //TODO: Add identifier as /api/snakes/:id
        return fetch("/api/snake", fetchoptions)
            .then(function(response){
               if(response.ok) {
                   return response.json();
               }
            }).then(function(result){
                return Promise.resolve(result);
        });
    }
}
