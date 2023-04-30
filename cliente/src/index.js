import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { BrowserRouter } from 'react-router-dom';
import Menu from './navegacao/Menu';
import Content from './navegacao/Content';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <div className='corpo'>
    <React.StrictMode>
      <BrowserRouter>
        <Menu />
        <Content />
      </BrowserRouter>
    </React.StrictMode>
  </div>
);

