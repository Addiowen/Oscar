package com.compulynx.compas.config;

public class DbQueries {

  public static final String FIND_CHARGE_BY_TRANSACTION_CODE =
      "select c from Charges c where c.transactionCode = :transactionCode";
  public static final String FIND_CHARGE_TYPE =
      "select t from ChargeTypeTransactionCode t where t.transactionCode = :transactionCode";
  public static final String FIND_MATRIX_TYPE =
      "select mt from ChargeMatrixType mt where mt.transactionCode = :transactionCode";
  public static final String FIND_CHARGE_MATRIX =
      "select cm from ChargeMatrix cm where cm.transactionCode = :transactionCode and (:amount >= cm.amountFrom and :amount <= cm.amountTo)";
  public static final String APPROVE_REVERSAL =
      "update Reversals r set r.approved = ?1, r.approvedBy = ?2, r.approvedAt = ?3 where r.id = ?4";
  public static final String GET_FIXED_PERCENT_CHARGES =
      "select t.id as 'transaction_code', name as 'transaction_name', charge_type, charge_amount, charge_percent, excise_duty from dbo.Charges c join dbo.transaction_types t on c.transaction_code = t.id join dbo.chargetype_transactioncode ct on t.id = ct.transaction_code where charge_type in('F','P')";
  public static final String GET_RANGE_CHARGES =
      "select t.id as 'transaction_code', name as 'transaction_name', amount_from, amount_to, cm.charge_amount, range_type, cg.excise_duty from dbo.Charge_Matrix cm join dbo.transaction_types t on cm.transaction_code = t.id join dbo.chargetype_transactioncode ct on t.id = ct.transaction_code join dbo.Charges cg on cm.transaction_code = cg.transaction_code where ct.charge_type = 'R'";
  public static final String TRANSACTIONS_BY_TELLER_ID =
      "select * from transactions t where t.transaction_type_id = :transType and t.teller_id = :telId order by created_at asc";
  public static final String FIND_APPROVER_BY_BRANCH =
      "select u.username, u.email, b.name as 'branch', g.GroupName as 'groupName', r.RightDisplayName as 'rightName'\n"
          + "  from users u\n"
          + "  join bank_branch b on u.branchId = b.branch_code\n"
          + "  join usergroupsmaster g on u.group_id = g.id\n"
          + "  join userassignedrights ur on ur.GroupId = g.id\n"
          + "  join rightsmaster r on ur.rightId = r.id\n"
          + "  where r.RightDisplayName = :approvalType and b.branch_code = :branch";
}
