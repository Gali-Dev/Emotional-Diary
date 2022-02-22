import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import styled from 'styled-components';
//import jwt_decode from 'jwt-decode';
import { login } from '../../store';

const LoginStyle = styled.div`
  display: grid;
`;
const TitleStyle = styled.div`
  font-size: 60px;
  text-align: center;
  // border: 1px solid #003458;
  margin: 100px 10px 0px 10px;
`;
const ImageStyle = styled.img`
  // margin-top: 100px;
  width: 100%;
  //border: 1px solid #003458;
`;
const LoginBoxStyle = styled.div`
  display: grid;
  height: 500px;
  grid-template-columns: 50% 50%;
  //border: 1px solid #003458;
  align-items: center;
  //justify-items: center; 절대 쓰면 안됨 % 깨짐
  margin: 10px 10px;
  //padding: 0px 10px 0px 0px;
`;
const LabelStyle = styled.div`
  font-size: 23px;
`;
const ButtonBoxStyle = styled.div`
  display: grid;
  grid-template-columns: 20% 20% 20%;
  grid-column-gap: 30px;
  justify-content: end;
`;
const FormStyle = styled.form`
  margin: 20px;
`;
const ButtonStyle = styled.button`
  width: 80px;
`;
const Login = () => {
  const [user, setUser] = useState({
    id: '',
    pw: '',
  });
  const dispatch = useDispatch();
  const history = useHistory();

  function inputHandle(e) {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  }

  function join(e) {
    history.push('/join');
  }

  function reset(e) {
    e.preventDefault();
    setUser({
      id: '',
      pw: '',
    });
  }

  const submitLogin = (e) => {
    e.preventDefault();

    fetch('http://10.100.102.31:8000/loginProc', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(user),
    })
      .then((res) => {
        console.log('rrr', res);
        for (let header of res.headers.entries()) {
          if (header[0] === 'authorization') {
            localStorage.setItem('Authorization', header[1]);
          } else {
          }
        }
        // console.log('hhh', res.text());
        return res.text();
      })
      .then((res) => {
        console.log(res);
        if (res === 'ok') {
          //유저네임 저장
          alert('로그인 되었습니다.');
          dispatch(login());
          fetch('http://10.100.102.31:8000/member/get', {
            method: 'GET',
            headers: {
              Authorization: localStorage.getItem('Authorization'),
            },
          })
            .then((res) => res.json())
            .then((res) => {
              localStorage.setItem('userNo', res.mno);
              localStorage.setItem('userId', res.id);
              localStorage.setItem('userName', res.name);
              history.push('/diary');
            });
        } else {
          alert('아이디 혹은 비번을 다시 입력하세요!');
        }
      });
  };
  return (
    <LoginStyle>
      <TitleStyle>당신의 감정은 어떠한가요 ?</TitleStyle>
      <LoginBoxStyle>
        <ImageStyle src="images/logo.png" alt="" />
        <FormStyle>
          <div className="form-group">
            <LabelStyle>아이디</LabelStyle>
            <input
              className="form-control form-control-lg"
              type="text"
              name="id"
              onChange={inputHandle}
              value={user.id}
            />
          </div>
          <div className="form-group">
            <LabelStyle>비밀번호</LabelStyle>
            <input
              className="form-control form-control-lg"
              type="password"
              name="pw"
              onChange={inputHandle}
              value={user.pw}
            />
          </div>
          <ButtonBoxStyle>
            <ButtonStyle
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={join}
            >
              회원가입
            </ButtonStyle>
            <ButtonStyle
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={submitLogin}
            >
              로그인
            </ButtonStyle>
            <ButtonStyle
              type="button"
              className="btn btn-secondary btn-sm"
              onClick={reset}
            >
              취소
            </ButtonStyle>
          </ButtonBoxStyle>
        </FormStyle>
      </LoginBoxStyle>
    </LoginStyle>
  );
};

export default Login;
