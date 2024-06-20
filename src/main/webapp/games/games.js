// Deze javascript file is puur een 'voorbeeld-frontend' en -hoeft- niet aangepast te worden. In principe hoef je alleen
// de ...-service.js files in te vullen met de juiste promise-logica en dan zou de UI moeten werken.
// Uiteraard -mag- je deze files wel aanpassen, want heel fraai is deze UI nou ook weer niet:)

import GamesService from "./games-service.js";

let gamesList = document.getElementById('games');
let gamesService = new GamesService();
let details = document.getElementById('details');

window.addEventListener('hashchange', () => {
    loadGame(window.location.hash)
});

function loadGame(hashId) {
    let id = hashId.substring(1);
    return gamesService.getReplay(id).then(gameDetails => {
        if (gameDetails) {
            let game_details = document.querySelector('#game-details');

            let game_id = game_details.querySelector('#game-id');
            game_id.textContent = gameDetails.gameid;

            let game_res = game_details.querySelector('#game-result');
            game_res.textContent = gameDetails.gameresult;

            let turn = game_details.querySelector('#game-turns');
            turn.textContent = gameDetails.turns;

            let game_algo = game_details.querySelector('#game-algorithm');
            game_algo.textContent = gameDetails.algorithm;

            let snakeid = game_details.querySelector('#snake-id');
            snakeid.textContent = gameDetails.snakeid;

            let snakeip = game_details.querySelector('#snake-ip');
            snakeip.textContent = gameDetails.inProgress;

            details.classList.remove('hidden');
        } else {
            details.classList.add('hidden')
        }
    });
}

function refresh() {
    gamesList.innerHTML = '';
    return gamesService.getGameIds().then(ids => {
        for (let id of ids) {
            let li = document.createElement('li');
            li.innerHTML = `<a href='#${id}'>Game ${id}</a><span class="delete">&#x2716;</span>`;

            li.querySelector('.delete').addEventListener("click", () => {
                gamesService.removeReplay(id).then(() => {
                    return refresh();
                });
            })
            gamesList.appendChild(li);
        }
    });
}

refresh().then(() => {
    if (window.location.hash) {
        loadGame(window.location.hash)
    }
});
