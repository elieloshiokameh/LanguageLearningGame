import "../App.css";
function Sidebar() {

    const languages = ["Irish", "French", "Spanish"];

    return (
        <div className="Sidebar">
            <h1>Languages</h1>
            <div className="Sidebar-list">
                <button class="button button1">{ languages[0] }</button>
                <button class="button button1">{ languages[1] }</button>
                <button class="button button1">{ languages[2] }</button>
            </div>
            <div className="Sidebar-list">
                <button class="button button2">Statistics</button>
            </div>

        </div>
    );
}

export default Sidebar;