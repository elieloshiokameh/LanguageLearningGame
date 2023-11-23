import "../App.css";
import { Outlet, Link } from "react-router-dom";
import { useParams } from 'react-router-dom';

function Game() {

    const { flag } = useParams();

    return (
        <div>
           <nav>
                <h1>Game in {flag}</h1>
                <button><Link to="/">menu</Link></button>
                <button><Link to="/statistics">statistics</Link></button>
            </nav>
            <Outlet />
        </div>
    );
}

export default Game;