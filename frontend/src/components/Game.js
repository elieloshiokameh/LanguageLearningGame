import "../App.css";
import { useNavigate } from 'react-router-dom';
import { Outlet, Link } from "react-router-dom";
import { useParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';

function Game() {

    const navigate = useNavigate();

    const { flag } = useParams();

    const [seconds, setSeconds] = useState(5);
    const [isRunning, setIsRunning] = useState(false);

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
      };

    return (
        <div>
           <nav>
                {isRunning === false && (
                    <button onClick={startGame}>play</button>
                )}
                {isRunning === true && (
                    <div>
                        <h1>game {flag} {seconds}</h1>
                        <button><Link to={`/statistics/${seconds}`}>finish</Link></button>
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