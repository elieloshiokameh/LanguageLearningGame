import "../App.css";
import { useNavigate } from 'react-router-dom';
import { Outlet, Link } from "react-router-dom";
import { useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';

function Game() {

    const navigate = useNavigate();

    const { language1 } = useParams();
    const { language2 } = useParams();

    const [wordPair, setWordPair] = useState("");

    const [seconds, setSeconds] = useState(60);
    const [isRunning, setIsRunning] = useState(false);

    const fetchWordPair = async () => {
      try {
          console.log("Languages:", language1, language2)
          const response = await fetch(`http://localhost:8080/api/${language1}/${language2}/randomWord`);
          if (!response.ok) {
              throw new Error(`HTTP error! Status: ${response.status}`);
          }
          const data = await response.json();
          setWordPair(data);
      } catch (error) {
          console.error('Error fetching language data:', error);
      }
    };

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
    
    const startGame = () => {
        setIsRunning(true);
        fetchWordPair();
      };

    return (
        <div>
           <nav>
                {isRunning === false && (
                    <div>
                        <h1>game {language2}</h1>
                        <div className="buttons">
                           <button class="button" onClick={startGame}>play</button>
                        </div>
                    </div>
                )}
                {isRunning === true && (
                    <div>
                        <h1>seconds remaining: {seconds}</h1>
                        <h1>wordPair: {wordPair[0]} and {wordPair[1]}</h1>
                        <div className="buttons">
                          <Link to={`/statistics/${seconds}`}>finish</Link>
                        </div>
                        <Outlet />
                    </div>
                )}
                {seconds === 0 && (
                    navigate(`/statistics/${seconds}`)
                )}
            </nav>
        </div>
    );
}

export default Game;