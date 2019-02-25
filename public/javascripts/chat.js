$(function() {
    const ENTER_KEY = 13;

    const messageArea = $('div#messageArea');
    const newMessageInput = $('input#newMessageInput');
    const sendMessageButton = $('button#sendMessage');

    const fromUserId = parseInt($('input#fromUserId').val());
    const toUserId = parseInt($('input#toUserId').val());

    newMessageInput.focus();

    sendMessageButton.click(() => submitMessage());

    newMessageInput.keypress((e) => {
        if (e.which === ENTER_KEY) {
            submitMessage();
        }
    });

    function submitMessage() {
        const text = newMessageInput.val();
        if (text) {
            newMessageInput.val('');
            ws.send(JSON.stringify(messageJson(text)));
        }
    }

    const messageJson = (text) => {
        return {
            timestamp: formattedCurrentDatetime(),
            fromUserId: fromUserId,
            toUserId: toUserId,
            content: text
        };
    };

    const formattedCurrentDatetime = () => {
        const dt = new Date();
        return `${dt.getFullYear()}-${pad(dt.getMonth()+1, 2)}-${pad(dt.getDate(), 2)} ${pad(dt.getHours(), 2)}:${pad(dt.getMinutes(), 2)}:${pad(dt.getSeconds(), 2)}`;
    };

    function pad(num, max) {
        const str = num.toString();
        return str.length < max ? pad("0" + str, max) : str;
    }

    const ws = new WebSocket("ws://localhost:8080/ws")

    ws.onmessage = (event) => {
        const messageJson = JSON.parse(event.data);
        console.log("HERE");
        if (shouldDisplay(messageJson)) {
            console.log("DOING IT");
            prependMessageToDisplay(messageJson);
        }
        console.log("OUTSIDE")
    };

    // necessary because the websocket currently broadcasts all messages to all clients
    function shouldDisplay(messageJson) {
        return (messageJson.fromUserId === fromUserId && messageJson.toUserId === toUserId) ||
            (messageJson.fromUserId === toUserId && messageJson.toUserId === fromUserId);
    }

    function prependMessageToDisplay(messageJson) {
        const justifyClass = (messageJson.fromUserId === fromUserId) ? "justify-content-start" : "justify-content-end";
        const textAlignClass = (messageJson.fromUserId === fromUserId) ? "text-left" : "text-right";

        // TODO avoid code repeat with chat.scala.html
        messageArea.prepend($(
            `<div class="row ${justifyClass}">
                <div class="col-6 ${textAlignClass}">
                    <p class="message font-weight-bold">${messageJson.content}</p>
                    <small class="text-muted">${messageJson.timestamp}</small>
                </div>
            </div>`));
    }
});