class RenameUnionpaysColumnName < ActiveRecord::Migration
  def change
    rename_column :unionpays, :orderid, :orderno
  end
end
