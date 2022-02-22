import './App.css';
import { Route, Switch } from 'react-router-dom';
import Intro from './pages/Intro';
import Login from './pages/user/Login';
import Join from './pages/user/Join';
import DiaryHome from './components/DiaryHome';
import styled from 'styled-components';
import Footer from './components/Footer';

const AppStyle = styled.div`
  display: grid;
  grid-template-columns: 100%;
  justify-items: center;
  align-items: center;
  height: 1050px;
  width: 100%;
  font-family: 'TDTDTadakTadak';
  background-color: #eaeae3;
`;
const AppBoxStyle = styled.div`
  border: 5px solid #003458;
  border-radius: 10px;
  height: 850px;
  width: 80%;
  max-width: 1500px;
  margin: 50px 150px 0px 150px;
  background-color: #eaeae3;
`;
function App() {
  return (
    <AppStyle>
      <AppBoxStyle>
        <Switch>
          <Route exact path="/" component={Intro} />
          <Route path="/login" component={Login} />
          <Route path="/join" component={Join} />
          <Route path="/diary" component={DiaryHome} />
        </Switch>
      </AppBoxStyle>
      <Footer />
    </AppStyle>
  );
}

export default App;
