import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { AuthProvider } from './providers/AuthProvider';
import ProtectedRoute from './routes/ProtectedRoute';
import Login from './routes/Login';
import SignUp from './routes/SignUp';
import Dashboard from './routes/Dashboard';
import AdminCrud from './components/AdminCrud';
import AddUserCard from './components/AddUserCard';
import EditCard from './components/EditCard';

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
  {
    path: '/add',
    element: <AddUserCard/>
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
