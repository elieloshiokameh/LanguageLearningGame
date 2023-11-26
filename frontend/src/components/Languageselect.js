import "../App.css";
import Flag from 'react-world-flags';
import { Outlet, Link } from "react-router-dom";
import React, { useState, useEffect } from 'react';

function Languageselect() {

    const [languages, setLanguages] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/languages');
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            setLanguages(data);
        } catch (error) {
            console.error('Error fetching language data:', error);
        }
    };
        fetchData();
    }, []);

    return (
        <div>
            <nav>
                <h1>Language Select</h1>
                <div>
                    {languages ? 
                        <div>
                            {Object.entries(languages).map(([abv, name]) => (
                                <div className="buttons">
                                    <Link to={`/game/en/English/${abv}/${name}`}>{name}</Link>
                                </div>
                            ))}
                        </div> 
                    : "error languages not available."}
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Languageselect;