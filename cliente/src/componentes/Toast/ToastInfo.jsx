
import { useEffect } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const ToastInfo = (props) => {

    console.log("PROPS: ", props);


    const notfy = () => toast.info(props.mensagem, {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        });

        useEffect(() =>{
            if(props.show === true){
                props.setShow(false);
                notfy();
            }
        },[props.show])


    return (
        <div>
            <ToastContainer />
        </div>
    );
}

export default ToastInfo;