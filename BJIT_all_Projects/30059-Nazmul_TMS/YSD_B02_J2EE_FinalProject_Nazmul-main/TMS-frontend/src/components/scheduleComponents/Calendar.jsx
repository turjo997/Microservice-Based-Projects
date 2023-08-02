import React,{useEffect, useState} from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import './Calendar.css';

const Calendar = ({ transformedEvents, handleEventClick, isSidebarOpen  }) => {
    const [calendarHeight, setCalendarHeight] = useState('100%');
    const [calendarWidth, setCalendarWidth] = useState('100%');
    
    // console.log("in calendar: ", transformedEvents);
    const calendarOptions = {
        plugins: [dayGridPlugin, timeGridPlugin],
        initialView: 'timeGridDay',
        initialDate: '2023-07-27',
        slotEventOverlap: false,
        slotMinTime: '05:00:00', // Set the minimum time to 5 AM
        slotMaxTime: '20:59:59',
        scrollTime: '18:00:00',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay',
        },
        events:transformedEvents,
        eventClick: handleEventClick,
      };
      useEffect(() => {
        const updateCalendarHeight = () => {
          const screenHeight = window.innerHeight;
          const screenWidth = window.innerWidth;
          const calendarContainerPadding = 300; // Update with your calendar container padding
          const newHeight = screenHeight - calendarContainerPadding;
          const newWidth = screenWidth- calendarContainerPadding;
    
          if (isSidebarOpen) {
            setCalendarHeight(`${newHeight}px`);
            setCalendarWidth(`${newWidth}px`);
          } else {
            setCalendarHeight('100%');
            setCalendarWidth('100%');
          }
        };
    
        updateCalendarHeight();
        window.addEventListener('resize', updateCalendarHeight);
        
        return () => {
          window.removeEventListener('resize', updateCalendarHeight);
        };
      }, [isSidebarOpen]);
  return (
    // <FullCalendar
    // {...calendarOptions}
    // />
        <FullCalendar {...calendarOptions} />
    
  );
};

export default Calendar;
