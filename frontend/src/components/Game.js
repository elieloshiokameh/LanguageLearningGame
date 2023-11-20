import "../App.css";
function Game() {

    const timer = "TIMER"
    const keyword = "Rabbit"
    const language = "French"
    
    const answer = ["_ _ _ _ _"]

    return (
        <div className="Game">
            <div className="Timer">
                <h1>{ timer }</h1>
            </div>
            <div className="Question">
                <h1>What is the word for <span className="Keyword">{keyword}</span> in {language} ?</h1>
            </div>
            <div className="Answer-box"><span className="Answer">{ answer }</span></div>
            <button class="button button2">Hint</button>

        </div>
    );
}

export default Game;