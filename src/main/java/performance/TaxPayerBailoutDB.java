package performance;

public interface TaxPayerBailoutDB {

    int NUMBER_OF_RECORDS_DESIRED = 2 * 1000000;

    /**
     * 依据tax payer的id从数据库获取数据
     * @param id
     * @return
     */
    TaxPayerRecord get(String id);

    TaxPayerRecord add(String id, TaxPayerRecord record);

    TaxPayerRecord remove(String id);

    int size();

}
