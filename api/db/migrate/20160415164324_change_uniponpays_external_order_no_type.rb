class ChangeUniponpaysExternalOrderNoType < ActiveRecord::Migration
  def change
    change_column :unionpays, :ExternalOrderNo, :string
  end
end
