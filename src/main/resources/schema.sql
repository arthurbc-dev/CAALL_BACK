CREATE OR REPLACE FUNCTION fn_atualizar_timestamp_dictionary()
RETURNS TRIGGER
LANGUAGE plpgsql
AS '
BEGIN
    IF TG_OP = ''INSERT''
       OR NEW.ditionary IS DISTINCT FROM OLD.ditionary THEN

        NEW.timestamp_dictionary = CURRENT_TIMESTAMP;
END IF;

RETURN NEW;
END;
';

DROP TRIGGER IF EXISTS trg_atualizar_timestamp_dictionary
ON tb_dados_pessoais;

CREATE TRIGGER trg_atualizar_timestamp_dictionary
    BEFORE INSERT OR UPDATE ON tb_dados_pessoais
                         FOR EACH ROW
                         EXECUTE FUNCTION fn_atualizar_timestamp_dictionary();