import './App.css';
import Navbar from './components/Navbar';
import Sidebar from './components/Sidebar';
import Game from './components/Game';
import React, { useState } from 'react';

function App() {

  const [currentLanguage, setLanguage] = useState("Irish");

  const onLanguageSelect = (selectedLanguage) => {
    setLanguage(selectedLanguage);
  };

  return (
    <div className="App">
      <Navbar/>
      <div className="Main">
        <Sidebar onLanguageSelect={onLanguageSelect} />
        <Game keyword={"rabbit"} language={currentLanguage}/>
      </div>
    </div>
  );
}

export default App;
