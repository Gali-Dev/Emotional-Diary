import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { logout } from '../../store';

const ModifyStyle = styled.div`
  display: grid;
  grid-template-columns: auto;
  width: 100%;
  max-width: 850px;
  height: 100%;
  padding: 50px 10px 10px 10px;
  //border: 1px solid #003458;
`;
const LabelStyle = styled.div`
  font-size: 23px;
`;

const ButtonBoxStyle = styled.div`
  display: grid;
  grid-template-columns: auto auto auto;
  grid-column-gap: 10px;
  justify-content: end;
  margin: 10px;
`;
const Modify = (props) => {
  const id = props.match.params.id;
  const history = useHistory();
  const isLogin = useSelector((store) => store.isLogin);
  const dispatch = useDispatch();

  const [user, setUser] = useState({
    id: id,
    name: '',
    pw: '',
  });

  //화면 그려줌
  useEffect(() => {
    fetch('http://10.100.102.31:8000/member/get/', {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.json())
      .then((res) => {
        setUser(res);
      });
  }, []);

  //회원 수정
  function submitModify(e) {
    e.preventDefault(e);

    fetch('http://10.100.102.31:8000/member/update', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json; charset=utf-8',
        Authorization: localStorage.getItem('Authorization'),
      },
      body: JSON.stringify(user),
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          alert('회원 정보 수정이 되었습니다.');
          localStorage.setItem('userName', user.name);
          history.push('/diary');
        } else {
          alert('회원 정보 수정을 실패하였습니다.');
        }
      });
  }

  //회원 탈퇴
  function secession(e) {
    e.preventDefault(e);
    if (window.confirm('정말로 탈퇴하시겠습니까?')) {
      fetch('http://10.100.102.31:8000/member/delete', {
        method: 'delete',
        headers: {
          Authorization: localStorage.getItem('Authorization'),
        },
      })
        .then((res) => res.text())
        .then((res) => {
          if (res === 'ok') {
            alert('탈퇴되었습니다.');
            localStorage.clear();
            dispatch(logout());
            console.log(isLogin);
            history.push('/login');
          } else {
            alert('탈퇴를 실패했습니다.');
          }
        });
    } else {
      history.push('/diary');
    }
  }
  //input 값 들고옴
  function inputHandle(e) {
    setUser({
      ...user,
      [e.target.name]: e.target.value,
    });
  }
  return (
    <ModifyStyle>
      <form>
        회원 정보를 수정 해주세요
        <div className="form-group">
          <LabelStyle>userId</LabelStyle>
          <input
            className="form-control form-control-lg"
            type="text"
            name="id"
            onChange={inputHandle}
            value={user.id}
            readOnly={true}
          />
        </div>
        <div className="form-group">
          <LabelStyle>userName</LabelStyle>
          <input
            className="form-control form-control-lg"
            type="text"
            name="name"
            onChange={inputHandle}
            value={user.name}
          />
        </div>
        <div class="form-group">
          <LabelStyle>pw</LabelStyle>
          <input
            className="form-control form-control-lg"
            type="password"
            name="pw"
            onChange={inputHandle}
            value={user.pw}
          />
        </div>
        {/*   <div class="form-group">
          <LabelStyle>pw 확인 </LabelStyle>
          <input
            class="form-control form-control-lg"
            type="password"
            name="rePw"
            value={user.rePassword}
            onChange={inputHandle}
          />
        </div> */}
        <ButtonBoxStyle>
          <button
            type="button"
            className="btn btn-secondary btn-sm"
            onClick={secession}
          >
            회원 탈퇴
          </button>
          <Link to="/diary">
            <button type="button" className="btn btn-secondary btn-sm">
              취소
            </button>
          </Link>
          <button
            type="button"
            className="btn btn-secondary btn-sm"
            onClick={submitModify}
          >
            수정
          </button>
        </ButtonBoxStyle>
      </form>
    </ModifyStyle>
  );
};

export default Modify;
