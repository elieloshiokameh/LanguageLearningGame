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

    const handleEnterPress = (event) => {
        if (event.key === 'Enter') {
          console.log('User input:', userInput);
          if(currentWordPair === 4) {
            navigate(`/statistics/${seconds}`)
          }
          else {
            nextWordPair();
          }
        }
      };

    return (
        <div>
            <nav>
                {isLoadingGame && (
                    <div>
                        <h1>Loading game...</h1>
                    </div>
                )}
                {!isLoadingGame && !isRunning && (
                    <div>
                        <h1>game: {language2} to {language1}</h1>
                        <div className="buttons">
                            <button className="button" onClick={startGame}>
                                play
                            </button>
                        </div>
                    </div>
                )}
                {!isLoadingGame && isRunning && (
                    <div>
                        <h1>seconds remaining: {seconds}</h1>
                        <div>
                            <h1>{language2}: "{wordPairs[currentWordPair][1]}" = {language1}:
                                <input
                                type="text"
                                placeholder="Enter your data"
                                value={userInput}
                                onChange={handleInputChange}
                                onKeyDown={handleEnterPress}
                                />
                            </h1>
                        </div>
                        <div className="buttons">
                          <Link to={`/statistics/${seconds}`}>finish</Link>
                        </div>
                        <Outlet />
                    </div>
                )}
                {!isLoadingGame && seconds === 0 && navigate(`/statistics/${seconds}`)}
            </nav>
        </div>
    );
};

export default Game;