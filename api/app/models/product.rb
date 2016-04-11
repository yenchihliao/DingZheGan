class Product < ActiveRecord::Base
  self.primary_key = 'ProductSN'

  belongs_to :vendor
end
