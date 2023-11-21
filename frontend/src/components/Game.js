import "../App.css";
import React, { useState } from 'react';

function Game() {

    const timer = "TIMER"
    const keyword = "Rabbit"
    const language = "French"

    const [typedWord, setTypedWord] = useState(Array(keyword.length).fill(''));
      
    const handleInputChange = (index, value) => {
        const newTypedWord = [...typedWord];
        newTypedWord[index] = value;
        setTypedWord(newTypedWord);
    };

    return (
        <div className="Game">
            <div className="Timer">
                <h1>{ timer }</h1>
            </div>
            <div className="Question">
                <h1>What is the word for <span className="Keyword">{keyword}</span> in {language} ?</h1>
            </div>
            <div>
                {typedWord.map((letter, index) => (
                <input key={index} type="text" value={letter} maxLength={1} onChange={(e) => handleInputChange(index, e.target.value)}/>
                ))}
            </div>
            <button class="button button2">Hint</button>

        </div>
    );
}

export default Game;