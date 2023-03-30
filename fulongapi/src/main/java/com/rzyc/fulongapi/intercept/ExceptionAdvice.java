package com.rzyc.fulongapi.intercept;

import com.common.utils.model.Code;
import com.common.utils.model.Message;
import com.common.utils.model.SingleResult;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class ExceptionAdvice {

    protected final static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    public SingleResult<String> handleArithmeticException(ArithmeticException e){
        logger.info("ArithmeticException message -> "+e.getMessage());
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.ERROR.getCode());
        result.setMessage(Message.ERROR);
        return result;
    }

    /**
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SizeLimitExceededException.class)
    public SingleResult<String> handleSizeLimitExceededException(SizeLimitExceededException e){
        logger.info("SizeLimitExceededException message -> "+e.getMessage());
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.ERROR.getCode());
        result.setMessage("文件过大！");
        return result;
    }

    /**
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public SingleResult<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        logger.info("MaxUploadSizeExceededException message -> "+e.getMessage());
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.ERROR.getCode());
        result.setMessage("文件过大！");
        return result;
    }


    /**
     * 通一异常处理
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public SingleResult<String> handleConstraintViolationException(ConstraintViolationException e){
        String message = e.getLocalizedMessage();
        logger.info("ConstraintViolationException message -> "+message);
        String [] strs = e.getLocalizedMessage().split(":");
        if(strs.length >= 2 ){
            message = strs[1];
        }
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.EX_PARAM.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 通一异常处理
     * @return
     */
    @ExceptionHandler(BindException.class)
    public SingleResult<String> handleBindException(BindException e){
        logger.info("BindException message -> "+e.getMessage());
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.EX_PARAM.getCode());
        result.setMessage(e.getBindingResult().getFieldError().getDefaultMessage());
        return result;
    }

    /**
     * 参数异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public SingleResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        logger.info("MethodArgumentNotValidException message -> "+e.getMessage());
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.EX_PARAM.getCode());
        result.setMessage(e.getMessage());
        return result;
    }

    /**
     * token授权处理
     * @return
     */
    @ExceptionHandler(TokenException.class)
    public SingleResult<String> handleTokenException(Exception e){
        logger.info("TokenException message -> "+e.getMessage());
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.TOKEN_EXPIRE.getCode());
        result.setMessage(e.getMessage());
        return result;
    }

    /**
     * 通一异常处理
     * @return
     */
    @ExceptionHandler(Exception.class)
    public SingleResult<String> handleException(Exception e){
        logger.info("Exception message -> "+e.getMessage());
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.ERROR.getCode());
        result.setMessage(Message.ERROR);
        e.printStackTrace();
        return result;
    }

}
