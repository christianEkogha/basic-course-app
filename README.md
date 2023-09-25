# basic-course-app

Course manager

Create an API to manage race creation.

~~~yml
race:
  id: long
  name: string
  startAt: dateTimeLocale
  endAt: dateTimeLocale
  where: string
  starterIds: list<long>
  podium: 
    1: 
      id: long
      name: string
    2:
      id: long
      name: string
    3:
      id: long
      name: string

starter:
  id: long
  surname: string
  lastname: string
  firstname: string
  experience: long

podium:
  id: long
  
~~~