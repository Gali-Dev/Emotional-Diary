import React from 'react';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';

const IntroStyle = styled.div`
  display: grid;
  grid-template-columns: auto;
  justify-items: center;
  font-size: 45px;
  padding: 30px 0px 30px 0px;
`;
const ImageStyle = styled.img`
  margin-top: 100px;
  width: 80%;
  max-width: 1500px;
  padding: 0px 0px 100px 0px;
`;
const TitleStyle = styled.div`
  font-size: 45px;
  padding: 0px 0px 100px 0px;
`;
const Intro = () => {
  const history = useHistory();

  function start() {
    history.push('/login');
  }
  return (
    <IntroStyle>
      <ImageStyle src="images/logo2.png" alt="" />
      <TitleStyle>당신의 속마음을 펼쳐보아요</TitleStyle>
      <button type="button" className="btn btn-primary btn-lg" onClick={start}>
        시작하기
      </button>
    </IntroStyle>
  );
};

export default Intro;
