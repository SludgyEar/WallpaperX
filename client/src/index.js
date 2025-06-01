import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Login from './routes/Login';
import SignUp from './routes/SignUp';
import Dashboard from './routes/Dashboard';
import ProtectedRoute from './routes/ProtectedRoute';
import { AuthProvider } from './providers/AuthProvider';
import LayOut from './layouts/LayOut';

const router = createBrowserRouter([
  // Rutas no protegidas
  {
    path: '/',
    element: <Login/>,
  },
  {
    path: '/signup',
    element: <SignUp/>
  },
  {
    path: '/',
    element: <ProtectedRoute/>,
    children: [
      {
        path: '/dashboard',
        element: <Dashboard/>
      }
    ]
  },
  //Test
  {
    path: '/test',
    element: <LayOut/>
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>
);

reportWebVitals();
