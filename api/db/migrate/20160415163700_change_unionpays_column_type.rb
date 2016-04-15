class ChangeUnionpaysColumnType < ActiveRecord::Migration
  def change
    change_column :unionpays, :ExternalOrderNo, :integer
  end
end
