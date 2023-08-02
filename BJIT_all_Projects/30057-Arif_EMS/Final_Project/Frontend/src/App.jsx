import 'bootstrap/dist/css/bootstrap.min.css'
// import "./App.css";
import AdminRegistrationForm from './pages/AdminRegistrationForm/AdminRegistrationForm'
import TrainerRegistrationForm from './pages/Trainer/TrainerRegistrationForm'
import TraineeRegistrationForm from './pages/Trainee/TraineeRegistrationForm'
import LoginForm from './pages/Login/LoginForm'
import { Route, Routes } from 'react-router-dom'
import Header from './compoments/Header'
import CreateBatch from './pages/Batch/CreateBatch'
import CreateTask from './pages/Task/CreateTask'
import FinalMarks from './pages/FinalMark/FinalMarks'
import Home from './pages/Home/Home'
import AllBatch from './pages/AllBatch/AllBatch'
import LogOut from './LogOut/LogOut'
// import FinalTask from "./pages/FinalMark/FinalMarks"
function App() {
  const userId = localStorage.getItem('userId');
  const role = localStorage.getItem('role');
  return (
    <>
      <Header role={role}/>
      <Routes>
        <Route path='/' element={<LoginForm />}></Route>
        <Route path='/admin' element={<AdminRegistrationForm />}></Route>
        <Route path='/trainer' element={<TrainerRegistrationForm />}></Route>
        <Route path='/trainee' element={<TraineeRegistrationForm />}></Route>
        <Route path='/createbatch' element={<CreateBatch />}></Route>
        <Route path='/createtask' element={<CreateTask userId={userId} role={role} />} />
        <Route path='/finalmark' element={<FinalMarks />} />
        <Route path='/home' element={<Home />} />
        <Route path='/allBatch' element={<AllBatch userId={userId} />} />
        <Route path='/logout' element={<LogOut/>} />
      </Routes>
    </>
  )
}

export default App
