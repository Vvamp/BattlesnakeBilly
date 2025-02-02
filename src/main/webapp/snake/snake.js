// Deze javascript file is puur een 'voorbeeld-frontend' en -hoeft- niet aangepast te worden. In principe hoef je alleen
// de ...-service.js files in te vullen met de juiste promise-logica en dan zou de UI moeten werken.
// Uiteraard -mag- je deze files wel aanpassen, want heel fraai is deze UI nou ook weer niet:)
import SnakeService from "./snake-service.js";

let service = new SnakeService();
let form = document.forms.snake;
let formItems = document.querySelectorAll('input:not([disabled=true]), select');
let updateBtn = document.getElementById('update-btn');

function update() {
    return service.updateSnake({
        color: form.color.value,
        head: form.head.value,
        tail: form.tail.value,
        mctstime: form.mctstime.value
    }).then(() => {
        updateBtn.disabled = true;
    })
}


updateBtn.addEventListener('click', update);
form.addEventListener('change', () => {
    updateBtn.disabled = false;
});

form.addEventListener('submit', (e) => {
    e.preventDefault(); //Dit form submit via javascript, dus dan willen we niet dat het scherm gaat knipperen
});

function refresh(snakeDTO) {
    form.author.value = snakeDTO.author;
    form.color.value = snakeDTO.color;
    form.head.value = snakeDTO.head;
    form.tail.value = snakeDTO.tail;
    form.mctstime.value = snakeDTO.mctstime;
}


updateBtn.disabled = true;
for (let input of formItems) {
    input.disabled = true;
}

service.getSnake().then(currentValues => {
    refresh(currentValues);

    for (let input of formItems) {
        input.disabled = false;
    }
});

service.getSnakeOptions().then(customizationOptions => {
    let headList = document.querySelector("#head-selector");
    let tailList = document.querySelector("#tail-selector");
    customizationOptions.heads.forEach(elem => {
        let newHeadOption = new Option(elem, elem);
        headList.add(newHeadOption);
    });

    customizationOptions.tails.forEach(elem => {
        let newTailOption = new Option(elem, elem);
        tailList.add(newTailOption);    });
});