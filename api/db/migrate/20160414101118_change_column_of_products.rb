class ChangeColumnOfProducts < ActiveRecord::Migration
  def change
    change_column :products, :SellPriceCNY, :integer
  end
end
