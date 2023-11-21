import './App.css';
import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';
import Game from './components/Game';

function App() {
  return (
    <div className="App">
      <Navbar/>
      <div className="Main">
        <Sidebar/>
        <Game keyword={"rabbit"} language={"French"}/>
      </div>
    </div>
  );
}

export default App;
