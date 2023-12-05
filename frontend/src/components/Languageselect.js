import "../App.css";
import Flag from 'react-world-flags';
import { Outlet, Link } from "react-router-dom";
import React, { useState, useEffect } from 'react';

function Languageselect() {

    const [languages, setLanguages] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
        try {
            const response = await fetch('/api/languages',{credentials: 'include'});
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
            <nav className="menu">
                <h1>Language Select</h1>
                <div className="language-select">
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