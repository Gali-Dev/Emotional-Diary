import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';

const MyDiaryWriteStyle = styled.div`
  display: grid;
  grid-template-columns: auto;
  width: 100%;
  max-width: 850px;
  height: 100%;
  padding: 40px 20px 10px 20px;
`;
const TitleStyle = styled.div`
  font-size: 40px;
  height: 10%;
  text-align: center;
`;
/* const ButtonBoxStyle = styled.div`
  display: grid;
  grid-template-columns: auto auto auto;
  grid-column-gap: 10px;
  margin: 10px;
`; */
const ButtonBoxStyle = styled.div`
  display: grid;
  grid-template-columns: 20% 20%;
  margin: 40px 0px 0px 0px;
  justify-content: end;
`;
const ButtonStyle = styled.button`
  width: 80px;
`;
/* const MyDiaryFormStyle = styled.form`
  display: grid;
  align-content: center;
  grid-template-columns: 100%;
  max-width: 100%;
  padding: 10px 10px;
  border: 1px solid #003458;
`; */
const DiaryWrite = (props) => {
  console.log('write: ', props.location.state.tno);
  const history = useHistory();
  const date = props.match.params.date;
  const id = localStorage.getItem('userNo');
  const [diary, setDiary] = useState({
    memberId: id,
    tno: props.location.state.tno,
    title: '',
    contents: '',
    createTime: date,
  });

  function inputHandle(e) {
    setDiary((prevState) => {
      // 함수형으로 쓰는 이유 : setstate 두번쓸때 값을 들고오기 우ㅐㅎ서
      return {
        ...prevState,
        [e.target.name]: e.target.value,
      };
    });
  }
  function submitWrite(e) {
    e.preventDefault();

    fetch('http://10.100.102.31:8000/board/save', {
      method: 'POST',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(diary),
    })
      .then((res) => res.text())
      .then((res) => {
        console.log(res);
        console.log('gkdl', diary);
        if (res === 'ok') {
          alert('글이 등록되었습니다.');
          if (props.location.state.tno === 0) {
            history.push('/diary');
          } else {
            history.push('/diary/our/' + props.location.state.tno);
          }
        } else {
          alert('글 등록이 실패하였습니다.');
        }
      });
  }
  return (
    <MyDiaryWriteStyle>
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
        ></textarea>
        <ButtonBoxStyle>
          <ButtonStyle className="btn btn-secondary" onClick={submitWrite}>
            작성
          </ButtonStyle>
          <Link to="/diary">
            <ButtonStyle className="btn btn-secondary">취소</ButtonStyle>
          </Link>
        </ButtonBoxStyle>
      </form>
    </MyDiaryWriteStyle>
  );
};

export default DiaryWrite;
