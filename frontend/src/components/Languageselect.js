import "../App.css";
import Flag from 'react-world-flags';
import { Outlet, Link } from "react-router-dom";

function Languageselect() {

    return (
        <div className="Languageselect">
            <nav>
                <h1>Language Select</h1>
                <div className="buttons">
                    <Link to="/game/Irish"><Flag code="irl" height="32"/></Link>
                    <Link to="/game/French"><Flag code="fra" height="32"/></Link>
                    <Link to="/game/Spanish"><Flag code="esp" height="32"/></Link>
                </div>
            </nav>
            <Outlet />
        </div>
    );
}

export default Languageselect;