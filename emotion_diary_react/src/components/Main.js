import React from 'react';
import { Route, Switch } from 'react-router-dom';
import styled from 'styled-components';
import DiaryModify from '../pages/diary/DiaryModify';
import DiaryWrite from '../pages/diary/DiaryWrite';
import DiaryDetail from '../pages/diary/DiaryDetail';
import MyDiary from '../pages/diary/MyDiary';
import Modify from '../pages/user/Modify';
import OurDiary from '../pages/diary/OurDiary';
import OurDiaryJoin from '../pages/diary/OurDiaryJoin';
import OurDiaryModify from '../pages/diary/OurDiaryModify';
import Analysis from '../pages/diary/Analysis';
import OurDiaryApply from '../pages/diary/OurDiaryApply';

const MainStyle = styled.div`
  //스크롤때문에 안맞아 보일수 있음 but 맞음
  display: grid;
  grid-template-columns: 100%;
  height: 90%;
  width: 100%;
  justify-items: center;
  align-items: center;
  overflow: scroll;
  border: 3px solid #003458;
  border-radius: 10px;
  background-color: #eaeae3;
  padding: 0px 10px 0px 10px;

  &::-webkit-scrollbar {
    //세로 스크롤 넓이
    width: 8px;

    // 가로 스크롤 높이
    height: 8px;

    border-radius: 6px;
    background: rgba(255, 255, 255, 0.4);
  }
  &::-webkit-scrollbar-thumb {
    background-color: rgba(0, 0, 0, 0.3);
    border-radius: 6px;
  }
`;

const Main = () => {
  return (
    <MainStyle>
      <Switch>
        <Route exact path="/diary/" component={MyDiary} />
        <Route path="/diary/our/:id" component={OurDiary} />
        <Route path="/diary/usermodify/:id" component={Modify} />
        <Route path="/diary/modify/:id" component={DiaryModify} />
        <Route path="/diary/detail/:id" component={DiaryDetail} />
        <Route path="/diary/write/:date" component={DiaryWrite} />
        <Route path="/diary/join" component={OurDiaryJoin} />
        <Route path="/diary/ourmodify/:id" component={OurDiaryModify} />
        <Route path="/diary/analysis" component={Analysis} />
        <Route path="/diary/apply" component={OurDiaryApply} />
      </Switch>
    </MainStyle>
  );
};

export default Main;
