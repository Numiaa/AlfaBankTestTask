const url = './api/';

function getGif() {
    let code = $("#codes").val();
    $.ajax({
        url: url + 'get-gif/' + code,
        method: 'GET',
        dataType: "json",
        complete: function (data) {
            let content = JSON.parse(data.responseText);
            let img = document.createElement("img");
            img.src = content.data.images.original.url;
            let gifKey = document.createElement("p");
            gifKey.textContent = content.id;
            let result = document.createElement("p");
            result.textContent = content.result;
            let out = document.querySelector("#out");
            out.innerHTML = '';
            out.insertAdjacentElement("afterbegin", img);
            out.insertAdjacentElement("afterbegin", gifKey);
            out.insertAdjacentElement("afterbegin", result);
        }
    })
}

function loadSelect() {
    $.ajax({
        url: url + 'get-codes',
        method: 'GET',
        complete: function (data) {
            let codesList = JSON.parse(data.responseText);
            let select = document.querySelector("#codes");
            select.innerHTML = '';
            for (let i = 0; i < codesList.length; i++) {
                let option = document.createElement("option");
                option.value = codesList[i];
                option.text = codesList[i];
                select.insertAdjacentElement("beforeend", option);
            }
        }
    })
}