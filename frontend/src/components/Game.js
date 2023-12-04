import "../App.css";
import { useNavigate } from 'react-router-dom';
import { Outlet, Link } from "react-router-dom";
import { useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';

function Game() {

    const navigate = useNavigate();

    const { language1abv } = useParams();
    const { language2abv } = useParams();

    const { language1 } = useParams();
    const { language2 } = useParams();

    const [seconds, setSeconds] = useState(60);
    const [isRunning, setIsRunning] = useState(false);

    const [isLoadingGame, setIsLoadingGame] = useState(false);

    const [currentWordPair, setCurrentWordPair] = useState(0);

    const [wordPairs, setWordPairs] = useState([]);

    var fetchWordPairs = async () => {
      try {
          const numberOfPairs = 5;
          let wordPairs = [];
  
          for (let i = 0; i < numberOfPairs; i++) {
              const response = await fetch(`http://localhost:8080/api/${language1abv}/${language2abv}/randomWord`);
              
              if (!response.ok) {
                  throw new Error(`HTTP error! Status: ${response.status}`);
              }
  
              const data = await response.json();
              wordPairs.push(data);
          }
  
          console.log("word pairs set");
          return wordPairs;
      } catch (error) {
          console.error('Error fetching language data:', error);
          return []; // Return an empty array in case of an error
      }
  };

    const [userInput, setUserInput] = useState('');

    useEffect(() => {
        let timer;
    
        if (isRunning && seconds > 0) {
          timer = setInterval(() => {
            setSeconds((prevSeconds) => prevSeconds - 1);
          }, 1000);
        } else if (seconds === 0) {
          setIsRunning(false);
        }
    
        return () => clearInterval(timer);
      }, [isRunning, seconds]);
    
    const startGame = async () => {
        setIsLoadingGame(true);
        const fetchedWordPairs = await fetchWordPairs();
        setWordPairs(fetchedWordPairs);
        setIsRunning(true);
        setIsLoadingGame(false);
    };

    const nextWordPair = () => {
        if(currentWordPair < 4) {
            setCurrentWordPair(currentWordPair + 1);
        }
    };

    const handleInputChange = (event) => {
        setUserInput(event.target.value);
      };

    const [answers, setAnswers] = useState([]);

    useEffect(() => {
        if (answers.length === 5) {
            let count = 0;
            for(let i = 0; i < wordPairs.length; i++) {
                let resp = answers[i];
                let ans = wordPairs[i][0];
                resp = resp.toLowerCase();
                ans = ans.toLowerCase();
                if(resp === ans) {
                    count = count + 1;
                }
                else {
                    console.log("Incorrect");
                }
            }
            navigate(`/statistics/${seconds}/${count}`);
          }
      }, [answers]);

    const handleEnterPress = (event) => {
        if (event.key === 'Enter') {
          console.log('User input:', userInput);
      
          setAnswers(answers => [...answers, userInput])
          nextWordPair();
          setUserInput("");
        }
      };

    return (
        <div>
            <nav>
                {isLoadingGame && (
                    <div className="game-menu">
                        <h1>Loading game...</h1>
                    </div>
                )}
                {!isLoadingGame && !isRunning && (
                    <div className="game-menu">
                        <h1>{language2} to {language1}</h1>
                        <div className="buttons">
                            <button className="button" onClick={startGame}>
                                play
                            </button>
                        </div>
                    </div>
                )}
                {!isLoadingGame && isRunning && (
                    <div className="game-menu">
                        <div className="game-seconds">
                            <h1>{seconds}</h1>
                        </div>
                        <div className="game-body">
                            <div className="game-box">
                                <h1>{language2}</h1>
                                <h1>"{wordPairs[currentWordPair][1]}"</h1>
                            </div>
                            <div className="game-box">
                                <h1>{language1}</h1>
                                <div className="game-input">
                                    <input
                                        type="text"
                                        placeholder="Enter your data"
                                        value={userInput}
                                        onChange={handleInputChange}
                                        onKeyDown={handleEnterPress}
                                    />
                                </div>
                            </div>
                        </div>
                        <div className="buttons">
                          <Link to={`/statistics/${seconds}/0`}>quit</Link>
                        </div>
                        <Outlet />
                    </div>
                )}
                {!isLoadingGame && seconds === 0 && navigate(`/statistics/${seconds}/0`)}
            </nav>
        </div>
    );
};

export default Game;