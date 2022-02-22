import React, { useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';

const ModifyStyle = styled.div`
  display: grid;
  grid-template-columns: auto;
  width: 100%;
  max-width: 850px;
  height: 100%;
  padding: 40px 20px 10px 20px;
  // border: 1px solid #003458;
`;
const TitleStyle = styled.div`
  font-size: 40px;
  height: 10%;
  text-align: center;
`;
const ButtonBoxStyle = styled.div`
  display: grid;
  grid-template-columns: 20% 20%;
  margin: 40px 0px 0px 0px;
  justify-content: end;
`;
const ButtonStyle = styled.button`
  width: 80px;
`;
const DiaryModify = (props) => {
  const [diary, setDiary] = useState({});
  const id = props.match.params.id;
  const history = useHistory();

  useEffect(() => {
    fetch('http://10.100.102.31:8000/board/get/' + id, {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.json())
      .then((res) => {
        setDiary(res);
      });
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  function submitModify(e) {
    e.preventDefault(e);
    console.log('submitModify() 실행');

    if (window.confirm('수정하시겠습니까?')) {
      fetch('http://10.100.102.31:8000/board/' + id, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
          Authorization: localStorage.getItem('Authorization'),
        },
        body: JSON.stringify(diary),
      })
        .then((res) => res.text())
        .then((res) => {
          if (res === 'ok') {
            alert('수정되었습니다.');
            history.push('/diary');
          } else {
            alert('수정 실패했습니다.');
          }
        });
    } else {
    }
  }
  function inputHandle(e) {
    setDiary((prevState) => {
      // 함수형으로 쓰는 이유 : setstate 두번쓸때 값을 들고오기 우ㅐㅎ서
      return {
        ...prevState,
        [e.target.name]: e.target.value,
      };
    });
  }
  return (
    <ModifyStyle>
      <form>
        <TitleStyle> 오늘 무슨일 있었나요?</TitleStyle>
        <label>제목</label>
        <input
          className="form-control form-control-lg"
          type="text"
          name="title"
          value={diary.title}
          onChange={inputHandle}
        />
        <br />
        <label>내용</label>
        <textarea
          className="form-control form-control-lg"
          name="contents"
          rows="8"
          onChange={inputHandle}
          value={diary.contents}
        />
        <ButtonBoxStyle>
          <Link to="/diary">
            <ButtonStyle className="btn btn-secondary btn-sm">취소</ButtonStyle>
          </Link>
          <ButtonStyle
            className="btn btn-secondary btn-sm"
            onClick={submitModify}
          >
            수정
          </ButtonStyle>
        </ButtonBoxStyle>
      </form>
    </ModifyStyle>
  );
};

export default DiaryModify;
