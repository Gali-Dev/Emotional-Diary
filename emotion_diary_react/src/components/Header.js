import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { logout } from '../store';

const HeaderStyle = styled.div`
  display: grid;
  width: 100%;
  height: 90%;
  justify-content: space-between;
  grid-template-columns: 100%;
  border: 3px solid #003458;
  text-align: left;
  border-radius: 10px;
  padding: 100px 10px 10px 10px;
  font-size: 20px;
  background-color: #eaeae3;
`;
const ImgStyle = styled.img`
  width: 100%;
`;
const TitleStyle = styled.div`
  width: 100%;
  text-align: center;
  font-size: 30px;
`;
const UserBoxStyle = styled.div`
  display: grid;
  grid-template-columns: auto;
  justify-content: right;
`;
const ButtonStyle = styled.button`
  background-color: transparent;
  font-family: 'TDTDTadakTadak';
  font-size: ${(props) => (props.user ? '15px' : '20px')};
  border: transparent;
  height: fit-content;
  outline: transparent;
  text-align: start;
  font-weight: bold;
  :focus {
    border: none;
    outline: none;
  }
`;
const GroupStyle = styled.div`
  display: grid;
  padding-left: ${(props) => (props.title ? '0px' : '20px')};
  grid-template-columns: ${(props) => (props.title ? '60% 50%' : '65% 35%')};
`;
const Header = () => {
  const isLogin = useSelector((store) => store.isLogin);
  const user = localStorage.getItem('userName');
  const dispatch = useDispatch();

  const [groups, setGroups] = useState([]);
  useEffect(() => {
    fetch('http://10.100.102.31:8000/tmember/get', {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.json())
      .then((res) => {
        setGroups(res);
      });
  }, []);

  //로그아웃
  function submitLogout() {
    if (window.confirm('정말로 로그아웃하시겠습니까?')) {
      localStorage.clear();
      dispatch(logout());
      console.log(isLogin);
    } else {
    }
  }
  function modify(tno) {
    window.location.replace('/diary/ourmodify/' + tno);
  }
  function refreshPage(id) {
    window.location.replace('/diary/our/' + id);
  }
  return (
    <HeaderStyle>
      <ImgStyle alt="" src="/images/logo.png" />
      <TitleStyle>{user}님</TitleStyle>
      <UserBoxStyle>
        <Link to={'/diary/usermodify/' + user}>
          <ButtonStyle user>회원 정보 수정</ButtonStyle>
        </Link>
        <Link to="/login">
          <ButtonStyle user onClick={submitLogout}>
            로그아웃
          </ButtonStyle>
        </Link>
      </UserBoxStyle>
      <Link to="/diary/">
        <ButtonStyle>나의 일기</ButtonStyle>
      </Link>
      <GroupStyle title>
        <ButtonStyle>우리일기</ButtonStyle>
      </GroupStyle>
      <GroupStyle title>
        <Link to="/diary/apply">
          <ButtonStyle>
            <span class="badge badge-pill badge-success">가입하기</span>
          </ButtonStyle>
        </Link>
        <Link to="/diary/join">
          <ButtonStyle>
            <span class="badge badge-pill badge-success">만들기</span>
          </ButtonStyle>
        </Link>
      </GroupStyle>

      {groups.map(({ tmno, member, together }) => (
        <GroupStyle>
          <ButtonStyle
            user
            key={tmno}
            onClick={() => refreshPage(together.tno)}
          >
            {together.tname}
          </ButtonStyle>
          {/*    <button type="button" className="btn btn-warning btn-sm">
                수정
              </button> */}
          <ButtonStyle>
            <span
              class="badge badge-pill badge-warning"
              onClick={() => modify(together.tno)}
            >
              수정
            </span>
          </ButtonStyle>
        </GroupStyle>
      ))}
      <Link to="/diary/analysis">
        <ButtonStyle>내글 분석</ButtonStyle>
      </Link>
    </HeaderStyle>
  );
};

export default Header;
