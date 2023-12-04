import "../App.css";
import { Outlet, Link } from "react-router-dom";
import React, { useState, useEffect } from 'react';

function Statistics() {

    const [stats, setStats] = useState([]);
    const [isValidTable, setTable] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/games',{credentials: 'include'});
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            setStats(data);
            console.log("data being fetched:", data);
            setTable(true);
        } catch (error) {
            console.error('Error fetching games data:', error);
        }
    };
        fetchData();
    }, []);

    return (
        <div>
           <nav className="menu">
                <h1>Statistics</h1>
                {isValidTable && (
                <table className="stats-table">
                    <thead>
                        <tr>
                            <th>Correct</th>
                            <th>Questions</th>
                            <th>Time Remaining</th>
                            <th>Time Played</th>
                        </tr>
                    </thead>
                    <tbody>
                        {stats.correct.map((value, index) => (
                            <tr key={index}>
                            <td>{value}</td>
                            <td>{stats.questions[index]}</td>
                            <td>{stats.timeRemaining[index]}</td>
                            <td>{new Date(stats.timePlayed[index]).toLocaleString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                )}
                <div className="buttons">
                    <Link to="/languageselect">Play again</Link>
                    <Link to="/">Menu</Link>
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Statistics;