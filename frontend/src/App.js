import './App.css';
import Sidebar from './components/Sidebar';
import Navbar from './components/Navbar';
import Game from './components/Game';

function App() {
  return (
    <div className="App">
      <div className="Nav">
        <Navbar/>
      </div>
      <div className="Main">
        <Sidebar/>
        <Game/>
      </div>
    </div>
  );
}

export default App;
