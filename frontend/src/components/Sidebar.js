import "../App.css";
import Flag from 'react-world-flags';

function Sidebar() {

    const languages = ["Irish", "French", "Spanish"];

    return (
        <div className="Sidebar">
            <h1>Languages</h1>
            <div className="Sidebar-list">
                <button class="button button1">
                    <Flag class="flag" code="irl" height="32" />
                    <h3>{ languages[0] }</h3>
                </button>
                <button class="button button1">
                    <Flag class="flag" code="fr"  height="32" />
                    <h3>{ languages[1] }</h3>
                </button>
                <button class="button button1">
                    <Flag class="flag" code="es"  height="32" />
                    <h3>{ languages[2] }</h3>
                </button>
            </div>
            <div className="Sidebar-list">
                <button class="button button2">Statistics</button>
            </div>

        </div>
    );
}

export default Sidebar;