import { motion } from 'framer-motion'
import styled from 'styled-components'
import Fondo from '../assets/fondoLogin.jpg'

export const ContenedorLogin = styled(motion.div)`
    min-height: 100vh;
    width: 100%;
    background: #0058ff;
    background-image: url(${Fondo});
    background-repeat: no-repeat;
    background-size: cover;
    position: relative;
    display: grid;
    place-content: center;
    overflow-x: hidden;
`
export const MainContainer = styled.div`
  display: grid;
  place-content: center;
`
export const LogoBox = styled.div`
display: flex;
justify-content: center;
align-items: center;
margin-bottom: 40px;
    img{
      width: 250px;
      object-fit: cover;
    }
    @media (max-width: 1000px) {
      img{
      width: 100px;
      }
    }
`
export const BoxIngresar = styled.div`
  display: flex;
`
export const RoboxBox = styled.div`
  position: relative;
  width: 300px;
  height: 100%;
  @media (max-width: 1000px) {
    width: 100px;
  }
`
export const ImgRobot = styled.div`
  img{
    position : absolute;
    left: 8%;
    width: 80%;
  }
  @media (max-width: 1000px) {
    img{
      left: -45%;
    }
  }
`
export const ImgConsultora = styled.div`
  img{
    width: 200px;
    object-fit: cover;
    position : absolute;
    right: 3%;
    bottom: 10%;
  }
  @media (max-width: 1400px){
    img{
      width: 120px;
      bottom: 6%;
    }
  }
`
export const MainBox = styled.div`
  height: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr;
  /* @media (max-height: 800px) {
      padding-top: 10%;
    } */
  @media (max-width: 850px) {
    padding-top: 10%;
    display: flex;
    flex-direction: column;
    gap: 30px;
  }
  
`
export const Box1 = styled.div`
  p{
    font-size: 35px;
    text-align: center;
    color: #fff;
    padding-bottom: 50px;
  }
  h3{
    font-size: 70px;
    color: #fff;
    text-align: center;
  }
  img{
    display: block;
    margin: auto;
  }
  @media (max-height: 800px) {
      p{
      font-size: 15px;
      }
      h3{
        font-size: 35px;
      }
      img{
        width: 80%;
      }
    }
    @media (max-width: 850px) {
      h3{
        font-size: 25px;
      }
      p{
      font-size: 20px;
      }
      img{
        width: 80%;
      }
    }
`
export const Box2 = styled.div`
    display: flex;
    justify-content: center;
    background: #0438af;
    border-radius: 20px;
    margin: 0px 40px;
    @media (max-width: 850px) {
      margin: 40px 10px;
    }
`
export const Sesion = styled.div`
  margin-left: -80px;
  padding: 50px 200px;
  background: #f5f5f5ce;
  border-radius: 20px;
  text-align: center;
  h3{
    font-size: 50px;
    font-weight: 700;
    color: #000;
  }
  @media (max-width: 1600px) {
      padding: 40px 150px;
      h3{
        font-size: 25px;
      }
    }
    @media (max-width: 500px) {
      padding: 20px 20px;
      h3{
        font-size: 25px;
      }
      p{
      font-size: 20px;
      }
    }
`
export const Form = styled.form`
  padding-top: 20px;
`
export const FormBox = styled.div`
position: relative;
  input{
    width: 100%;
    height: 50px;
    border: 2px solid #aaa;
    border-radius: 20px;
    margin: 8px 0;
    outline: none;
    padding: 8px;
    box-sizing: border-box;
    transition: 0.3s;
    padding-left: 20px;
    &:focus{
    border-color: dodgerBlue;
    box-shadow: 0 0 8px 0 dodgerBlue;
            }
    }
  i{
    position: absolute;
    left: 80%;
    top: 15px;
    padding: 9px 18px;
    color: #aaa;
    transition: 0.3s;
  }
  @media (max-width: 1000px){
    i{
    left: 70%;
  }
  }
  input:focus + i{
    color: dodgerBlue;
  }
  .btn{
    font-size: 20px;
    border: none;
    background: #c44343;
    color: #fff;
    padding-left: 0;
    cursor: pointer;
  }
`
export const ErrorFormLogin = styled.div`
  display: grid;
  place-items: center;
`
export const BoxError = styled.div`
  width: 100%;
  border: 0;
  border-radius: 10px;
  background: rgb(218, 71, 71);
  margin-top: 10px;
  h3{
    margin: 10px;
    font-size: 15px;
    padding: 2px 10px;
    text-align: center;
    color: #fff;
  }
  @media (max-width: 1600px) {
    width: 250px;
    }
`
