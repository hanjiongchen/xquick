package co.xquick.booster.exception;

/**
 * 错误编码
 * 1000以下为内置错误,参考HTTP状态码
 *
 * 1000以上，由5位数字组成，前2位为模块编码，后3位为业务编码
 *
 * @author Charles (zhanngchaoxu@gmail.com)
 */
public interface ErrorCode {

    int SUCCESS = 0;

    int INTERNAL_SERVER_ERROR = 500;
    int ERROR_REQUEST = 400;
    int UNAUTHORIZED = 401;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int METHOD_NOT_ALLOWED = 405;

    int NOT_NULL = 10001;
    int DB_RECORD_EXISTS = 10002;
    int PARAMS_GET_ERROR = 10003;
    int ACCOUNT_PASSWORD_ERROR = 10004;
    int ACCOUNT_DISABLE = 10005;
    int IDENTIFIER_NOT_NULL = 10006;
    int CAPTCHA_ERROR = 10007;
    int SUB_MENU_EXIST = 10008;
    int PASSWORD_ERROR = 10009;
    int ACCOUNT_NOT_EXIST = 10010;
    int SUPERIOR_DEPT_ERROR = 10011;
    int SUPERIOR_MENU_ERROR = 10012;
    int DATA_SCOPE_PARAMS_ERROR = 10013;
    int DEPT_SUB_DELETE_ERROR = 10014;
    int DEPT_USER_DELETE_ERROR = 10015;
    int ACT_DEPLOY_ERROR = 10016;
    int ACT_MODEL_IMG_ERROR = 10017;
    int ACT_MODEL_EXPORT_ERROR = 10018;
    int UPLOAD_FILE_EMPTY = 10019;
    int TOKEN_NOT_EMPTY = 10020;
    int TOKEN_INVALID = 10021;
    int ACCOUNT_LOCK = 10022;
    int ACT_DEPLOY_FORMAT_ERROR = 10023;
    int OSS_UPLOAD_FILE_ERROR = 10024;
    int SEND_SMS_ERROR = 10025;
    int REDIS_ERROR = 10027;
    int JOB_ERROR = 10028;
    int INVALID_SYMBOL = 10029;
    int JSON_FORMAT_ERROR = 10030;
    int SMS_CONFIG_ERROR = 10031;
    int UNKNOWN_LOGIN_TYPE = 10032;
    int DB_RECORD_NOT_EXISTED = 10033;
    int SEND_PUSH_ERROR = 10034;
    int HAS_SUB_RECORD = 10035;
    int HAS_DUPLICATED_RECORD = 10036;
    int OSS_CONFIG_ERROR = 10037;
    int PUSH_CONFIG_ERROR = 10038;
    int MAIL_CONFIG_ERROR = 10039;
    int DB_VIOLATION_ERROR = 10040;
    int FILE_EXCEED_MAX_FILE_SIZE = 10041;
    int VERIFICATION_CODE_ERROR = 10042;
    int VERIFICATION_CODE_EXPIRED = 10043;
    int AES_ENCRYPT_ERROR = 10044;

    int WX_CONFIG_ERROR = 11000;
    int WX_API_ERROR = 11001;

}
