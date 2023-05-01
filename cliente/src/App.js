import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import './App.css';
import Menu from './navegacao/Menu';
import Content from './navegacao/Content';

function App() {

  return (
    <div className='corpo'>
    <React.StrictMode>
      <BrowserRouter>
        <Menu />
        <Content />
      </BrowserRouter>
    </React.StrictMode>
  </div>
  );
}

export default App;
