import FullCalendar from '@fullcalendar/react';
import interactionPlugin from '@fullcalendar/interaction';
import dayGridPlugin from '@fullcalendar/daygrid';
import bootstrapPlugin from '@fullcalendar/bootstrap';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useHistory } from 'react-router-dom';

const OurDiaryStyle = styled.div`
  display: grid;
  grid-template-columns: auto;
  grid-template-rows: 10% 90%;
  width: 100%;
  max-width: 850px;
  height: 100%;
`;
const TitleStyle = styled.div`
  font-size: 40px;
  height: 10%;
  text-align: center;
`;
const EventStyle = styled.div`
  display: grid;
  text-align: right;
  font-size: 16px;
  background-color: #9ddde9;
  border: 1px solid whitesmoke;
`;
const EmotionStyle = styled.img`
  width: 30%;
`;
const OurDiary = (props) => {
  const history = useHistory();
  const [diary, setDiary] = useState([]);
  const id = props.match.params.id;

  useEffect(() => {
    fetch('http://10.100.102.31:8000/board/group/' + id, {
      method: 'GET',
      headers: {
        Authorization: localStorage.getItem('Authorization'),
      },
    })
      .then((res) => res.json())
      .then((res) => {
        let db = res.map((ress, index) => {
          return (
            index,
            {
              id: ress.bno,
              title: ress.title,
              start: ress.createTime,
              emotion: ress.emotion,
              textColor: 'black',
              borderColor: 'whitesmoke',
            }
          );
        });
        setDiary(db);
      });
  }, []);

  function renderEventContent(eventInfo) {
    // 리스트 그리기
    return (
      <>
        <EventStyle>
          <EmotionStyle
            className="eventimage"
            alt=""
            src={
              '/images/' + eventInfo.event._def.extendedProps.emotion + '.png'
            }
          />
          <p>{eventInfo.event.title}</p>
        </EventStyle>
      </>
    );
  }
  function handleEventClick(clickInfo) {
    //클릭했을때 동작
    // 다이어리 등록되었을때 그 항목 클릭 -> 디테일페이지 이동하게 만들기
    console.log(clickInfo);
    history.push('/diary/detail/' + clickInfo.event.id);
  }

  function handleEvents() {}

  function handleDateSelect(selectInfo) {
    //history.push('/diary/write/' + selectInfo.startStr);
    history.push({
      state: { tno: id },
      pathname: '/diary/write/' + selectInfo.startStr,
    });
  }
  return (
    <OurDiaryStyle>
      <TitleStyle>우리의 일기</TitleStyle>
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin, bootstrapPlugin]}
        headerToolbar={{
          left: 'prev',
          center: 'title',
          right: 'next',
        }}
        themeSystem="bootstrap"
        contentHeight="auto"
        handleWindowResize={true}
        locale="ko"
        initialView="dayGridMonth"
        editable={true}
        selectable={true}
        eventStartEditable={false}
        selectMirror={true}
        dayMaxEvents={true}
        weekends={true}
        events={diary}
        select={handleDateSelect}
        eventContent={renderEventContent} // custom render function
        eventClick={handleEventClick}
        eventsSet={handleEvents}
      />
    </OurDiaryStyle>
  );
};

export default OurDiary;
